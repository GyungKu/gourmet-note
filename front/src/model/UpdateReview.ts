import type { RequestItem } from './RequestItem';
import type { RequestShop } from './RequestShop';

export interface UpdateReview {
  shop: RequestShop;
  content?: string;
  rating?: number;
  items?: RequestItem[];
  deleteImages: number[];
}
