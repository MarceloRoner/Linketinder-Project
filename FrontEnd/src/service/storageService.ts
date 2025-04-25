export interface StoragePort {
  save<T>(key: string, data: T): void;
  load<T>(key: string): T | null;
}

export class LocalStorageService implements StoragePort {
  save<T>(key: string, data: T): void {
    localStorage.setItem(key, JSON.stringify(data));
  }
  load<T>(key: string): T | null {
    const raw = localStorage.getItem(key);
    return raw ? (JSON.parse(raw) as T) : null;
  }
}

export const storageService = new LocalStorageService();
