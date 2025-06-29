import { useMapEvents } from 'react-leaflet';
import { FC } from 'react';

interface MapClickHandlerProps {

}

const MapClickHandler: FC<MapClickHandlerProps> = () => {
   useMapEvents({
      click(e) {
         console.log(e)
      },
   });

   return null; // không render gì
};

export default MapClickHandler;
