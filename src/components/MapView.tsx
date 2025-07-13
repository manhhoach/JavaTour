import { MapContainer, Marker, TileLayer, useMap, LayersControl } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import { FC, useEffect } from 'react';
import { LatLngExpression } from 'leaflet';
import MapClickHandler from './MapClickHandler';
import Coordinates from '../types/tour/Coordinates';

// Tọa độ mặc định (ví dụ: Hà Nội)
const defaultCenter: LatLngExpression = [21.028511, 105.854444];

interface MapViewProps {
   selectedCoords: Coordinates | null;
}

const FlyToHandler = ({ coords }: { coords: Coordinates }) => {
   const map = useMap();

   useEffect(() => {
      map.flyTo([coords.latitude, coords.longitude], 13, {
         duration: 1,
      });
   }, [coords]);

   return null;
};

const MapView: FC<MapViewProps> = ({ selectedCoords }) => {
   return (
      <MapContainer
         center={defaultCenter}
         zoom={13}
         scrollWheelZoom={true}
         style={{ height: '100vh', width: '100%' }}
      >
         <LayersControl position="topright">
            <LayersControl.BaseLayer checked name="Default Map">
               <TileLayer
                  attribution='&copy; OpenStreetMap contributors'
                  url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
               />
            </LayersControl.BaseLayer>

            <LayersControl.BaseLayer name="Satellite View">
               <TileLayer
                  attribution='Tiles &copy; Esri'
                  url='https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
               />
            </LayersControl.BaseLayer>
         </LayersControl>
         <MapClickHandler />
         {selectedCoords && (
            <>
               <FlyToHandler coords={selectedCoords} />
               <Marker position={[selectedCoords.latitude, selectedCoords.longitude]} />
            </>
         )}
      </MapContainer>
   );
};

export default MapView;
