import { FC } from "react";

interface NotFoundProps {
   message?: string;
}

const NotFound: FC<NotFoundProps> = ({ message = "No data found." }) => {
   return (
      <div className="flex flex-col items-center justify-center py-10 text-center text-gray-500">
         <p className="text-sm">{message}</p>
      </div>
   );
};

export default NotFound;
