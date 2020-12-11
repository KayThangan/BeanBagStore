# BeanBagStore

### Prerequisites
What software to install

```
install jdk
```
```
install Java 8+
```

## Running the jar file
Running the main application
```
cd CardGame
```
```
java -jar cards.jar
```
The text files will be created in the same filepath as the jar file.

## Running the JUnit Test
Running the test class (MAC OS/LINUX)
```
cd CardGame/CardTest/src/
```
```
javac -cp .:lib/*  com/cards/fourofakind/test/CardTest.java
```
```
java -cp .:lib/* org.junit.runner.JUnitCore com.cards.fourofakind.test.CardTest
```

Running the test class (WINDOWS)
```
cd CardGame/CardTest/src/
```
```
javac -cp ;lib\*  com\cards\fourofakind\test\CardTest.java
```
```
java -cp ;lib\* org.junit.runner.JUnitCore com.cards.fourofakind.test.CardTest
```

## Deployment
Open the text files for each deck & player to see the program's gameplay.

## Built With
* Java

## Contributing
* [Kay Thangan](https://github.com/KayThangan)


## Authors
* [Kay Thangan](https://github.com/KayThangan)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
