import React, { useEffect } from 'react';
import { Modal, Form, Input, InputNumber, message } from 'antd';
import TourDto from '../types/tour/TourDto';
import CustomUpload from './CustomUpload';
import useApi from '../common/useApi';
import { ApiResponse } from '../types/common/ApiResponse';
import { PagedResponse } from '../types/common/PagedResponse';


type Props = {
    open: boolean;
    onCancel: () => void;
    initialData: TourDto | null;
    refetch: () => Promise<ApiResponse<PagedResponse<TourDto>>>;
};

const TourFormModal: React.FC<Props> = ({ open, onCancel, initialData, refetch }) => {
    const [form] = Form.useForm();

    const { refetch: createTour } = useApi({
        url: 'tours',
        method: 'POST',
        auto: false,
    });
    const { refetch: editTour } = useApi({
        url: 'tours/' + initialData?.id,
        method: 'PUT',
        auto: false,
    });

    useEffect(() => {
        if (initialData) {
            form.setFieldsValue({
                name: initialData.name,
                description: initialData.description,
                location: initialData.location,
                imageUrls: initialData.imageUrls,
                coordinates: {
                    latitude: initialData.coordinates.latitude,
                    longitude: initialData.coordinates.longitude,
                },
            });
        } else {
            form.resetFields();
        }
    }, [initialData, form]);

    const handleFinish = async (values: any) => {
        const formData: Omit<TourDto, 'id'> = {
            name: values.name,
            description: values.description,
            location: values.location,
            imageUrls: values.imageUrls,
            coordinates: {
                latitude: values.coordinates.latitude,
                longitude: values.coordinates.longitude,
            },
        };

        try {
            initialData != null ? await editTour({ body: formData }) : await createTour({ body: formData });
            message.success("Tour created successfully!");
            form.resetFields();
            onCancel();
            await refetch();
        } catch (err: any) {
            message.error("Failed to create tour: " + (err.message || 'Unknown error'));
        }
    };

    return (
        <Modal
            open={open}
            title={initialData ? 'Edit Tour' : 'Create New Tour'}
            onCancel={() => {
                onCancel();
                form.resetFields();
            }}
            onOk={() => form.submit()}
            destroyOnHidden
        >
            <Form layout="vertical" form={form} onFinish={handleFinish}>
                <Form.Item
                    name="name"
                    label="Tour Name"
                    rules={[{ required: true, message: 'Please enter the tour name' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="description"
                    label="Description"
                    rules={[{ required: true, message: 'Please enter the description' }]}
                >
                    <Input.TextArea rows={3} />
                </Form.Item>

                <Form.Item
                    name="location"
                    label="Location"
                    rules={[{ required: true, message: 'Please enter the location' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="imageUrls"
                    label="Image"
                    rules={[{ required: true, message: 'Please upload an image' }]}
                >
                    <CustomUpload
                        maxSizeMB={5}
                        accept=".jpg,.jpeg,.png"
                        initialData={initialData?.imageUrls || []}
                        onUploadSuccess={(urls) => {
                            form.setFieldsValue({ imageUrls: urls });
                        }}
                    />
                </Form.Item>


                <Form.Item
                    name={['coordinates', 'latitude']}
                    label="Latitude"
                    rules={[{ required: true, message: 'Please enter the latitude' }]}
                >
                    <InputNumber style={{ width: '100%' }} />
                </Form.Item>

                <Form.Item
                    name={['coordinates', 'longitude']}
                    label="Longitude"
                    rules={[{ required: true, message: 'Please enter the longitude' }]}
                >
                    <InputNumber style={{ width: '100%' }} />
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default TourFormModal;
