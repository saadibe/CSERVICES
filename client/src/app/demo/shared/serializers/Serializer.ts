/**
* Can deserialize any entity T
* @param T the entity type.
*/
export abstract class Serializable<T> {

  /**
  * Deserialize an entity input to an object T.
  */
  abstract deserialize(input: any): T;


  isDefinedAndNotNull(input: any): boolean {
    return input != null;
  }

}
