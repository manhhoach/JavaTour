import { FC } from "react";

const Loading: FC = () => {
   return (
      <div className="flex items-center justify-center h-full w-full p-4">
         <div className="w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
      </div>
   );
};

export default Loading;
