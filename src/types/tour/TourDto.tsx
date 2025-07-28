import Coordinates from "./Coordinates";

type TourDto = {
   id: number;
   name: string;
   description: string;
   location: string;
   coordinates: Coordinates;
   imageUrls: string[];
};

export default TourDto;