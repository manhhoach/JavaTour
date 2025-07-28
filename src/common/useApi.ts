import { useEffect, useState } from "react";
import axios, { AxiosRequestConfig } from "axios";

export interface UseApiOptions {
   url: string;
   method?: "GET" | "POST" | "PUT" | "DELETE";
   params?: Record<string, any>;
   body?: any;
   headers?: Record<string, string>;
   auto?: boolean;
   baseUrl?: string;
}

export default function useApi<T = any>(options: UseApiOptions) {
   const {
      url,
      method = "GET",
      params = {},
      body,
      headers = {},
      auto = true,
      baseUrl = import.meta.env.VITE_API_BASE_URL || "",
   } = options;

   const [data, setData] = useState<T | null>(null);
   const [loading, setLoading] = useState(auto);
   const [error, setError] = useState<Error | null>(null);

   const fetchData = async (overrideOptions?: Partial<UseApiOptions>) => {
      setLoading(true);
      setError(null);

      const merged = {
         url,
         method,
         params,
         body,
         headers,
         baseUrl,
         ...overrideOptions,
      };

      const fullUrl = `${merged.baseUrl}${merged.url}`;

      const isFormData = merged.body instanceof FormData;

      const axiosConfig: AxiosRequestConfig = {
         url: fullUrl,
         method: merged.method,
         params: merged.params,
         data: merged.body,
         headers: {
            ...(isFormData ? {} : { "Content-Type": "application/json" }),
            ...merged.headers,
         },
      };

      try {
         const res = await axios<T>(axiosConfig);
         setData(res.data);
         return res.data;
      } catch (err: any) {
         setError(err);
         throw err;
      } finally {
         setLoading(false);
      }
   };

   useEffect(() => {
      if (auto) fetchData();
   }, [url, JSON.stringify(params)]); // re-fetch nếu url hoặc params thay đổi

   return { data, loading, error, refetch: fetchData };
}
