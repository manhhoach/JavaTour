export interface PagedResponse<T> {
    items: T[];
    currentPage: number;
    totalPages: number;
    totalElements: number;
}