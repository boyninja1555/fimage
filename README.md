<div align="center">
    <h1>fImage</h1>
    <p>The default, lightweight library for `.fimg` (fImage) files</p>
</div>

---

### About

This is the default implementation of the fImage file format, which isn't specified anywhere. It's so compact there are technically 2 versions of it. One is for images containing transparency, the other is for images without. This simple optimization has a ~25% size improvement. The image height is calculated on load/resize for 4 fewer bytes to worry about, but may be janky at times.

---

### Installation

Maven

```xml
<dependency>
    <groupId>com.boyninja1555</groupId>
    <artifactId>fimage</artifactId>
    <version>VERSION</version>
</dependency>
```

Gradle (Groovy)

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/boyninja1555/fimage")
    }
}

dependencies {
    implementation "com.boyninja1555:fimage:VERSION"
}
```

### Contributing

As a near unknown file format, we accept most contributions. Note that this library is not meant for tools like converters, but for easy reading/writing. We do not accept code that doesn't directly change the file format or modifies how reading/writing works. Example: A PNG-to-fImage converter pull request will be denied, but edits to the file format in a pull request will be accepted.

Please follow our [MIT license](LICENSE.md) while contributing.
