You can use any third-party Vaadin add-ons located in the [Vaadin Directory](https://vaadin.com/directory) or elsewhere.\
This example demonstrates the integration of the [Input Mask](https://vaadin.com/directory/component/input-mask-add-on) add-on.

To include the Vaadin add-on in your application, add the Vaadin Add-ons maven repository and the add-on dependency to the project's
`build.gradle`:

```groovy
repositories {
    maven {
        url 'https://maven.vaadin.com/vaadin-addons'
    }
    // ...
}

dependencies {
    implementation 'org.vaadin.addons.componentfactory:vcf-input-mask:3.0.0'
    // ...
}
```

After that, you can use the add-on components programmatically, see for example [InputMaskAddonSimpleSample.java]({currentPath}?tab=InputMaskAddonSimpleSample.java).

The add-on adds the `InputMask` component, based on the [imaskjs](https://imask.js.org/) JavaScript library.\
`InputMask` component allows to add an input mask to a Vaadin Flow component like `TextField` or `DatePicker`.\
More detailed information about mask pattern construction and configuration can be found on the website: [imaskjs :: guide](https://imask.js.org/guide.html#masked-pattern).
