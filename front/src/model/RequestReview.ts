import type { RequestItem } from './RequestItem';
import type { RequestShop } from './RequestShop';

export interface RequestReview {
  shop: RequestShop;
  content?: string;
  rating?: number;
  items?: RequestItem[];
  deleteImages?: number[];
}
