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

   const buildUrl = () => {
      const query = new URLSearchParams(params).toString();
      return `${baseUrl}${url}${query ? `?${query}` : ""}`;
   };

   const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
         const res = await fetch(buildUrl(), {
            method,
            headers: {
               "Content-Type": "application/json",
               ...headers,
            },
            body: method !== "GET" ? JSON.stringify(body) : undefined,
         });
         if (!res.ok) throw new Error(`HTTP error ${res.status}`);
         const json = await res.json();
         setData(json);
         return json;
      } catch (err: any) {
         setError(err);
      } finally {
         setLoading(false);
      }
   };

   useEffect(() => {
      if (auto) fetchData();
   }, [url, JSON.stringify(params)]); // refetch if url or params change

   return { data, loading, error, refetch: fetchData };
}