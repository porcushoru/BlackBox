# BlackBox

## Project Description

BlackBox is an application that encrypts medium-sized files using a randomly generated salt with the "SHA-512" algorithm and then decrypts them back to their original state. A passphrase is required for both encryption and decryption.

If the user loses access to the app or needs to restore their account, they can create a new account by deleting the contents of the credentials.txt file located in the same folder as the executable. Users need not worry about losing access to their files if the credentials are lost, as each file is encrypted using a passphrase of the user's choice.

## Key Features

- For GitHub: the point of access is the "LoginPage" class.

- Encrypts and decrypts files using the SHA-512 algorithm with salt
- User authentication with passphrase protection
- Option to recreate an account by deleting credentials.txt

## Technologies Used

- Java

## Installation and Setup

### Prerequisites

- Java JDK 17 or later
- Maven

### Installation Steps

1. **Clone the Repository**
    ```bash
    git clone https://github.com/porcusoru/BlackBox.git
    cd BlackBox
    ```

2. **Build the Project with Maven**
    ```bash
    mvn clean package
    ```

3. **Run the Application**
    ```bash
    java -jar target/blackbox-1.0-SNAPSHOT.jar
    ```

## Usage

The application interface is user-friendly and intuitive. Simply follow the on-screen instructions to encrypt and decrypt files. 

## Screenshots

No screenshots available. Discover the interface by using the application!

## Contribution Guidelines

Feel free to use, modify, and distribute BlackBox. There are no strict guidelines as this is a first-time project. Contributions are welcome to improve the functionality and security of the app.

## License

BlackBox is free to use, modify, and distribute. No strings attached.

## Contact Information

For any questions or feedback, please refer to the "Credits" button within the application.

## Future Plans

I plan to build more advanced and reliable applications in the future as I gain more experience in the field.

## Acknowledgments

Thank you for trying out BlackBox! Special thanks to anyone who provides feedback or contributes to the project.
