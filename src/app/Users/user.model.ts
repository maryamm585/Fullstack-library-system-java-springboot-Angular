export interface User {
    id: number;
    username: string;
    password: string;
    enabled: boolean;
    roles: string | null; }