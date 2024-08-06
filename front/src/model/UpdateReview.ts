import type { RequestItem } from './RequestItem';
import type { RequestShop } from './RequestShop';

export interface UpdateReview {
  shop: RequestShop;
  reviews?: string;
  raing?: number;
  items?: RequestItem[];
  deleteImages: number[];
}
