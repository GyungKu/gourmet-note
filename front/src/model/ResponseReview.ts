import type { ResponseImage } from './ResponseImage';
import type { ResponseItemReview } from './ResponseItemReview';
import type { Shop } from './Shop';

export interface ResponseReview {
  id: number;
  rating?: number;
  reviews?: string;
  shop: Shop;
  items?: ResponseItemReview[];
  images?: ResponseImage[];
  createdAt: string;
}
