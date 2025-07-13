import { FC } from "react";

interface ErrorMessageProps {
   error?: Error;
}

const ErrorMessage: FC<ErrorMessageProps> = ({ error }) => {
   return (
      <div className="p-4 bg-red-100 text-red-700 rounded-md text-sm text-center">
         ⚠️ {error?.message}
      </div>
   );
};

export default ErrorMessage;
