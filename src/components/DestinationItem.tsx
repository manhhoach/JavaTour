import { FC, useState } from "react";
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
   const [currentImgIndex, setCurrentImgIndex] = useState(0);

   const handleClick = () => {
      setSelectedTour(tour);
      onSelect(tour.coordinates);
   }
   const handlePrev = (e: React.MouseEvent) => {
      e.stopPropagation();
      setCurrentImgIndex((idx) => (idx === 0 ? imgUrls.length - 1 : idx - 1));
   };

   const handleNext = (e: React.MouseEvent) => {
      e.stopPropagation();
      setCurrentImgIndex((idx) => (idx === imgUrls.length - 1 ? 0 : idx + 1));
   };
   const imgUrls = (tour.imageUrls && tour.imageUrls.length > 0)
      ? tour.imageUrls.map(e => `${import.meta.env.VITE_API_BASE_URL}${e}`)
      : ['images/default.jpg'];
   return (
      <Card
         hoverable
         cover={
            <div style={{ position: "relative" }}>
               <img
                  alt={tour.name}
                  src={imgUrls[currentImgIndex]}
                  style={{ width: "100%", height: "200px", objectFit: "cover" }}
                  onClick={handleClick}
               />
               {imgUrls.length > 1 && (
                  <>
                     <Button
                        style={{
                           position: "absolute",
                           top: "50%",
                           left: 0,
                           transform: "translateY(-50%)",
                           backgroundColor: "rgba(0,0,0,0.3)",
                           border: "none",
                           color: "white",
                        }}
                        onClick={handlePrev}
                     >
                        ‹
                     </Button>
                     <Button
                        style={{
                           position: "absolute",
                           top: "50%",
                           right: 0,
                           transform: "translateY(-50%)",
                           backgroundColor: "rgba(0,0,0,0.3)",
                           border: "none",
                           color: "white",
                        }}
                        onClick={handleNext}
                     >
                        ›
                     </Button>
                  </>
               )}
            </div>
         }
         onClick={() => handleClick?.()}
         style={{ marginBottom: "1rem" }}
      >
         <Card.Meta title={tour.name} description={tour.location} />
         {isCurrent && (
            <div style={{ marginTop: "0.75rem" }}>
               <Space style={{ marginTop: "0.5rem" }}>
                  <Button icon={<AiFillInfoCircle />} onClick={() => onDetail?.(tour)} />
                  <Button type="primary" icon={<AiFillEdit />} onClick={() => onEdit?.(tour)} />
                  <Button danger icon={<AiFillDelete />} onClick={() => onDelete?.(tour)} />
               </Space>
            </div>
         )}
      </Card>
   );
};

export default DestinationItem;