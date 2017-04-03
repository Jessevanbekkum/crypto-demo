# Keyczar key generation

```
java -jar KeyczarTool-0.71j-113016.jar create --name=asym --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/asym --asymmetric=rsa --purpose=crypt

java -jar KeyczarTool-0.71j-113016.jar addkey --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/asym

java -jar KeyczarTool-0.71j-113016.jar promote --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/asym --version=1
```

## Symmetric

```
java -jar KeyczarTool-0.71j-113016.jar create --name=sym --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/sym --purpose=crypt

java -jar KeyczarTool-0.71j-113016.jar addkey --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/sym

java -jar KeyczarTool-0.71j-113016.jar promote --location=/Users/jvanbekkum/programming/crypto-demo/src/main/resources/keyczarkeys/sym --version=1
```
