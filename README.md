<div align="center">
    <h1>fImage</h1>
    <p>The default, lightweight library for <code>.fimg</code> (fImage) files</p>
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
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/boyninja1555/fimage")
        credentials {
            username = "YOUR_GITHUB_USERNAME"
            password = "YOUR_GITHUB_CLASSIC_TOKEN"
        }
    }
}

dependencies {
    implementation "com.boyninja1555:fimage:VERSION"
}
```

### Examples

2x2 checker pattern

```java
RGBA black = new RGBA(0f, 0f, 0f);
RGBA white = new RGBA(1f, 1f, 1f);
FImage image = new FImage(
        new FImageFlags(false),  // (alphaMode:false)
        2,                       // Width of 2 pixels
        List.of(
                black, white,
                white, black
        )
);
```

2x2 checker pattern (saved to file)

```java
RGBA black = new RGBA(0f, 0f, 0f);
RGBA white = new RGBA(1f, 1f, 1f);
FImage image = new FImage(
        new FImageFlags(false),  // (alphaMode:false)
        2,                       // Width of 2 pixels
        List.of(
                black, white,
                white, black
        )
);

image.save(new File("checker.fimg"));
```

1x1 green pixel with `0.5` alpha (50%)

```java
// `alphaMode` must be set to `true` so the pixels' alpha values aren't assumed to be 1.0 (100%) when the image is loaded
FImage image = new FImage(
        new FImageFlags(true),  // (alphaMode:true)
        1,                      // Width of 1 pixel
        List.of(RGBA.HALF_GREEN)  // Preset color for green with `0.5` alpha (50%)
);
```

Loading from file

```java
FImage image = FImage.fromFile(new File("image.fimg"));

// ...
```

Loading from file to existing image

```java
// ...

image.load(new File("image.fimg"));

// ...
```

Editing pixel on existing image

```java
// ...

image.setPixelAt(46, 302, RGBA.TRANSPARENT);  // When `alphaMode` is set to `false`, this will appear black

// ...
```

### Contributing

As a near unknown file format, we accept most contributions. Note that this library is not meant for tools like converters, but for easy reading/writing. We do not accept code that doesn't directly change the file format or modifies how reading/writing works. Example: A PNG-to-fImage converter pull request will be denied, but edits to the file format in a pull request will be accepted.

Please follow our [MIT license](LICENSE.md) while contributing.
