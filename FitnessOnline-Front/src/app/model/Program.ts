import { Comment } from './Comment';
import { Attribute } from './Attribute';
import { Image } from './Image';
import { CreatorInfo } from './CreatorInfo';
export class Program {
  id: number | null = -1;
  name: string = '';
  description: string = '';
  price: number = -1;
  location: string = '';
  creator: CreatorInfo = new CreatorInfo();
  category: string = '';
  contact: string = '';
  ending: string = '';
  difficultyLevel: number = 0;
  instructorInfo: string = '';
  comments: Array<Comment> = [];
  attributes: Array<Attribute> = [];
  images: Array<Image> = [];
}
