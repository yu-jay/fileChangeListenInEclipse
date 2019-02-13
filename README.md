# fileChangeListenInEclipse
In eclipse, listen file change .
## 注意
1.整个项目使用UTF-8编码格式，导入项目时可以改变项目编码格式为UTF-8;<br>
2.导入项目后可能会报错，找不到log4j和jay-common包，需要在manifest.mf文件里面，runtime选项下面classpath里面删掉这些引入的第三方jar包，然后重新加入这些第三方jar即可；<br>
