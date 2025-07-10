import { FC, useState } from 'react';
import DestinationItem from './DestinationItem';
import Coordinates from '../types/Coordinates';
import tours from '../data/tour';
import TourDto from '../types/TourDto';


type DestinationListProps = {
   onSelect: (coords: Coordinates) => void;
}

const DestinationList: FC<DestinationListProps> = ({ onSelect }) => {
   const [selectedTour, setSelectedTour] = useState<TourDto | null>(null);
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
