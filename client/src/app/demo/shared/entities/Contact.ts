/**
 * Represents a contact in the database.
 */
export class Contact {

  private id: string;
  private lastName: string;
  private firstName: string;
  private email: string; // Word 'mail' bloqued by WAF.
  private phone: string;

  constructor() {
  }

  /**
   * Update the id of the contact.
   *
   * @param name the new id of the contact.
   */
  setId(id: string): any {
    this.id = id;
  }

  /**
   * Indicates the id of the contact.
   *
   * @return the new id of the contact.
   */
  getId(): string {
    return this.id;
  }

  /**
   * Update the name of the contact.
   *
   * @param name the new name of the contact.
   */
  setLastName(lastName: string): any {
    this.lastName = lastName;
  }

  /**
   * Indicates the name of the contact.
   *
   * @return the new name of the contact.
   */
  getLastName(): string {
    return this.lastName;
  }

  /**
   * Update the name of the contact.
   *
   * @param name the new name of the contact.
   */
  setFirstName(firstName: string): any {
    this.firstName = firstName;
  }

  /**
   * Indicates the name of the contact.
   *
   * @return the new name of the contact.
   */
  getFirstName(): string {
    return this.firstName;
  }

  /**
   * Update the mail of the contact.
   *
   * @param mail the new mail of the contact.
   */
  setEMail(email: string): any {
    this.email = email;
  }

  /**
   * Indicates the mail of the contact.
   *
   * @return the mail of the contact.
   */
  getEMail(): string {
    return this.email;
  }

  /**
   * Update the phone of the contact.
   *
   * @param phone the new phone of the contact.
   */
  setPhone(phone: string): any {
    this.phone = phone;
  }

  /**
   * Indicates the phone of the contact.
   *
   * @return the phone of the contact.
   */
  getPhone(): string {
    return this.phone;
  }

}
