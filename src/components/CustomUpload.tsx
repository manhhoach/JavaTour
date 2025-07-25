import React, { useState } from 'react';
import { Upload, Button, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import useApi from './../common/useApi'; // đường dẫn tới hook useApi của bạn
import { UploadChangeParam, UploadFile } from 'antd/es/upload';

type CustomUploadProps = {
    onUploadSuccess?: (uploadedUrls: string[]) => void;
    maxSizeMB?: number;
    accept?: string;
    initialData: string[]
};

const CustomUpload: React.FC<CustomUploadProps> = ({
    onUploadSuccess,
    maxSizeMB = 10,
    initialData = [],
    accept = '.jpg,.jpeg,.png,.pdf,.docx',
}) => {

    const [imageUrl, setImageUrl] = useState<string[]>(initialData)

    const { refetch: uploadFiles } = useApi({
        url: 'common/upload',
        method: 'POST',
        auto: false,
    });

    const beforeUpload = (file: File) => {
        if (file.size / 1024 / 1024 > maxSizeMB) {
            message.error(`File must be smaller than ${maxSizeMB}MB!`);
            return Upload.LIST_IGNORE;
        }
        // validate more
        return true;
    };

    const handleChange = async (info: UploadChangeParam<UploadFile<any>>) => {
        const newFile = info.file;

        // Chỉ xử lý nếu là file mới được thêm và chưa xử lý
        if (!newFile.originFileObj || newFile.status === 'done') return;

        const formData = new FormData();
        formData.append('files', newFile.originFileObj);

        try {
            const res = await uploadFiles({ body: formData });
            message.success('Upload successful!');
            if (res?.data) {
                setImageUrl(prev => {
                    const updated = [...prev, ...res.data];
                    if (onUploadSuccess) onUploadSuccess(updated);
                    return updated;
                });
            }
        } catch (err: any) {
            message.error('Upload failed: ' + (err.message || 'Unknown error'));
        }
    };

    const handleRemove = (file: UploadFile<any>) => {
        setImageUrl(prev => {
            const updated = prev.filter(x => x !== file.name);
            if (onUploadSuccess) onUploadSuccess(updated);
            return updated;
        });
    };

    return (
        <div>
            <Upload
                multiple
                accept={accept}
                beforeUpload={beforeUpload}
                fileList={imageUrl.map(file => ({
                    uid: file,
                    name: file,
                    status: 'done',
                }))}
                onChange={handleChange}
                onRemove={handleRemove}
                customRequest={({ onSuccess }) => {
                    // Disable automatic upload, do nothing here
                    setTimeout(() => onSuccess && onSuccess("ok"), 0);
                }}
            >
                <Button icon={<UploadOutlined />}>Select File(s)</Button>
            </Upload>
        </div>
    );
};

export default CustomUpload;
