import { FC } from 'react';
import TourDto from '../types/TourDto';
import DestinationItem from './DestinationItem';
import Coordinates from '../types/Coordinates';

const tours: TourDto[] = [
   {
      id: 1,
      name: 'Hạ Long Bay',
      description: 'Vịnh đẹp nổi tiếng thế giới',
      location: 'Quảng Ninh',
      coordinates: { lat: 20.9101, lng: 107.1839 },
      imageUrl: 'https://example.com/halong.jpg',
   },
   {
      id: 2,
      name: 'Phố cổ Hội An',
      description: 'Di sản văn hóa thế giới',
      location: 'Quảng Nam',
      coordinates: { lat: 15.8801, lng: 108.338 },
      imageUrl: 'https://example.com/hoian.jpg',
   },
];

type DestinationListProps = {
   onSelect: (coords: Coordinates) => void;
}

const DestinationList: FC<DestinationListProps> = ({ onSelect }) => {
   return (
      <div style={{ padding: '1rem', overflowY: 'auto', height: '100vh' }}>
         {tours.map((tour) => (
            <DestinationItem key={tour.id} tour={tour} onSelect={onSelect} />
         ))}
      </div>
   );
};

export default DestinationList;
