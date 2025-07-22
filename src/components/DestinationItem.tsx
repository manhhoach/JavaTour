import { FC } from "react";
import { Button, Card, Space } from 'antd';
import TourDto from "../types/tour/TourDto";
import Coordinates from "../types/tour/Coordinates";
import { AiFillDelete, AiFillEdit, AiFillInfoCircle } from "react-icons/ai";

interface DestinationItemProps {
   tour: TourDto;
   onSelect: (coords: Coordinates) => void;
   setSelectedTour: (tour: TourDto | null) => void;
   isCurrent: boolean;
   onDelete: Function;
   onEdit: Function;
   onDetail: Function;
}


const DestinationItem: FC<DestinationItemProps> = ({ tour, onSelect, setSelectedTour, isCurrent, onDelete, onEdit, onDetail }) => {
   const handleClick = () => {
      setSelectedTour(tour);
      onSelect(tour.coordinates);
   }
   const imgUrl = tour.imageUrl ? tour.imageUrl : "images/default.jpg";
   return (
      <Card
         hoverable
         cover={<img alt={tour.name} src={imgUrl} />}
         onClick={() => handleClick()}
         style={{ marginBottom: '1rem' }}
      >
         <Card.Meta title={tour.name} description={tour.location} />
         {isCurrent && (
            <div style={{ marginTop: '0.75rem' }}>

               <Space style={{ marginTop: '0.5rem' }}>
                  <Button icon={<AiFillInfoCircle />} onClick={() => onDetail?.(tour)}>

                  </Button>
                  <Button type="primary" icon={<AiFillEdit />} onClick={() => onEdit?.(tour)}>

                  </Button>
                  <Button danger icon={<AiFillDelete />} onClick={() => onDelete?.(tour)}>

                  </Button>

               </Space>
            </div>
         )}
      </Card>
   );
};

export default DestinationItem;