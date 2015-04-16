Matlabs lack of threading meant that I needed a threaded sine wave generator class with un-interupted variable frequency/amplitude - this is that sine wave generator! It's pretty rough and ready at the moment - frequency isn't the true frequency of the tone, it's just an indicator - the higher the frequency, the higher the pitch. 

Usage:

```java
Synth s = new Synth(200);
s.start(); //Start tone
s.stop(); //Stop tone
s.setFrequency(500); //change frequency to 500Hz
s.setAmplitude(0.5); //set wave amplitude to 50%
```
or in Matlab (pretty much the same)

```matlab
javaaddpath('path/to/compiled/class');
synth = Synth(200);
s.start();
s.stop();
s.setFrequency(500);
s.setAmplitude(0.5);
```
