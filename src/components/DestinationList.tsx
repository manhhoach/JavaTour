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
   const { data, loading, error } = useApi<ApiResponse<PagedResponse<TourDto>>>({
      url: "tours/paged",
      params: {
      }
   });
   const [selectedTour, setSelectedTour] = useState<TourDto | null>(null);

   const tours = data?.data.items;
   if (loading) {
      return <Loading />
   }
   if (!tours || tours.length === 0) {
      return <NotFound />
   }
   if (error) {
      return <ErrorMessage error={error} />
   }
   return (
      <>
         <div style={{ padding: '1rem', overflowY: 'auto', height: '100vh' }}>
            {tours.map((tour) => (
               <DestinationItem key={tour.id} isCurrent={tour.id === selectedTour?.id} setSelectedTour={setSelectedTour} tour={tour} onSelect={onSelect} />
            ))}
         </div>
      </>

   );
};

export default DestinationList;
