# cripto-tpe
Trabajo Práctico Especial de Criptografía y Seguridad | ITBA 2018

### Requirements

Must have JCE (Java Cryptography Extension) installed.

Download [here](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html) (Java 8)

Instructions on installations for Windows/Linux are on downloaded zip file ReadMe.
For Mac OS see [steps](https://stackoverflow.com/questions/37741142/how-to-install-unlimited-strength-jce-for-java-8-in-os-x)

To run:

    java -jar stegobmp [PROGRAM ARGUMENTS]

To build and run the program:

    ./gradlew shadowJar
    cd build/libs
    java -jar cripto-tpe-all.jar [PROGRAM ARGUMENTS]
