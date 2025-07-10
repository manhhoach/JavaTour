import { FC } from "react";
import { Card } from 'antd';
import TourDto from "../types/TourDto";
import Coordinates from "../types/Coordinates";

interface DestinationItemProps {
   tour: TourDto;
   onSelect: (coords: Coordinates) => void;
   setSelectedTour: (tour: TourDto | null) => void;
   isCurrent: boolean;
}


const DestinationItem: FC<DestinationItemProps> = ({ tour, onSelect, setSelectedTour, isCurrent }) => {
   const handleClick = () => {
      setSelectedTour(tour);
      onSelect(tour.coordinates);
   }
   return (
      <Card
         hoverable
         cover={<img alt={tour.name} src={tour.imageUrl} />}
         onClick={() => handleClick()}
         style={{ marginBottom: '1rem' }}
      >
         <Card.Meta title={tour.name} description={tour.location} />
         {
            isCurrent && <div style={{ color: 'green', fontWeight: 'bold' }}>Current Destination</div>
         }
      </Card>
   );
};

export default DestinationItem;