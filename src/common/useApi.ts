import { useEffect, useState } from "react";

type Method = "GET" | "POST" | "PUT" | "DELETE";


export interface UseApiOptions {
   url: string;
   method?: Method;
   params?: Record<string, any>;
   body?: any;
   headers?: HeadersInit;
   auto?: boolean; // auto-fetch on mount
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
         ...overrideOptions,
      };

      try {
         const query = new URLSearchParams(merged.params || {}).toString();
         const fullUrl = `${merged.baseUrl || baseUrl}${merged.url}${query ? `?${query}` : ''}`;

         // Nếu body là FormData, không set Content-Type (để trình duyệt tự set)
         const isFormData = merged.body instanceof FormData;

         const res = await fetch(fullUrl, {
            method: merged.method,
            headers: {
               ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
               ...merged.headers,
            },
            body: merged.method !== 'GET' ? merged.body : undefined,
         });

         if (!res.ok) throw new Error(`HTTP error ${res.status}`);
         const json = await res.json();
         setData(json);
         return json;
      } catch (err: any) {
         setError(err);
         throw err;
      } finally {
         setLoading(false);
      }
   };

   useEffect(() => {
      if (auto) fetchData();
   }, [url, JSON.stringify(params)]); // refetch if url or params change

   return { data, loading, error, refetch: fetchData };
}