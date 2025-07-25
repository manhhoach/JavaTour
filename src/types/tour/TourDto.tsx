import Coordinates from "./Coordinates";

type TourDto = {
   id: number;
   name: string;
   description: string;
   location: string;
   coordinates: Coordinates;
   imageUrl: string[];
};

export default TourDto;