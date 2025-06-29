import { FC } from "react";
import { Card } from 'antd';
import TourDto from "../types/TourDto";
import Coordinates from "../types/Coordinates";

interface DestinationItemProps {
   tour: TourDto;
   onSelect: (coords: Coordinates) => void;
}


const DestinationItem: FC<DestinationItemProps> = ({ tour, onSelect }) => {
   return (
      <Card
         hoverable
         cover={<img alt={tour.name} src={tour.imageUrl} />}
         onClick={() => onSelect(tour.coordinates)}
         style={{ marginBottom: '1rem' }}
      >
         <Card.Meta title={tour.name} description={tour.location} />
      </Card>
   );
};

export default DestinationItem;