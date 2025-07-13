import Coordinates from "./Coordinates";

export interface CreateTour {
   name: string;
   description: string;
   location: string;
   coordinates: Coordinates;
   imageUrls: string[];
}