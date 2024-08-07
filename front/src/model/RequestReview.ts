import type { RequestItem } from './RequestItem';
import type { RequestShop } from './RequestShop';

export interface RequestReview {
  shop: RequestShop;
  reviews?: string;
  rating?: number;
  items?: RequestItem[];
  deleteImages?: number[];
}
