import { FC, useState } from 'react';
import DestinationItem from './DestinationItem';
import Coordinates from '../types/tour/Coordinates';
import TourDto from '../types/tour/TourDto';
import useApi from '../common/useApi';
import Loading from './Loading';
import NotFound from './NotFound';
import { ApiResponse } from '../types/common/apiResponse';
import { PagedResponse } from '../types/common/PagedResponse';
import ErrorMessage from './ErrorMessage';


type DestinationListProps = {
   onSelect: (coords: Coordinates) => void;
}

const DestinationList: FC<DestinationListProps> = ({ onSelect }) => {
   const [page, setPage] = useState(1);
   const [selectedTour, setSelectedTour] = useState<TourDto | null>(null);
   const { data, loading, error } = useApi<ApiResponse<PagedResponse<TourDto>>>({
      url: "tours/paged",
      params: {
         page: page,
         size: 2
      }
   });

   if (loading || !data) {
      return <Loading />
   }
   const { items: tours, totalPages } = data.data;

   if (!tours || tours.length === 0) {
      return <NotFound />
   }
   if (error) {
      return <ErrorMessage error={error} />
   }



   const handleDelete = (tour: TourDto) => {
      console.log("Deleting tour:", tour);
      // TODO: Gọi API xoá nếu có
   };

   const handleEdit = (tour: TourDto) => {
      console.log("Editing tour:", tour);
      // TODO: Chuyển hướng đến trang chỉnh sửa hoặc mở modal
   };

   const handleDetail = (tour: TourDto) => {
      console.log("Viewing detail of tour:", tour);
      // TODO: Hiển thị chi tiết, ví dụ mở modal hoặc điều hướng
   };

   return (
      <>
         <div style={{ padding: '1rem', overflowY: 'auto', height: '80vh' }}>
            {tours.map((tour) => (
               <DestinationItem
                  key={tour.id}
                  isCurrent={tour.id === selectedTour?.id}
                  setSelectedTour={setSelectedTour}
                  tour={tour}
                  onSelect={onSelect}
                  onDelete={handleDelete}
                  onDetail={handleDetail}
                  onEdit={handleEdit}
               />
            ))}
         </div>
         <div className="flex justify-center gap-4">
            <button
               disabled={page === 1}
               onClick={() => setPage((p) => p - 1)}
               className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
               Previous
            </button>
            <span className="text-sm self-center">Page {page} of {totalPages}</span>
            <button
               disabled={page === totalPages}
               onClick={() => setPage((p) => p + 1)}
               className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
               Next
            </button>
         </div>
      </>

   );
};

export default DestinationList;
