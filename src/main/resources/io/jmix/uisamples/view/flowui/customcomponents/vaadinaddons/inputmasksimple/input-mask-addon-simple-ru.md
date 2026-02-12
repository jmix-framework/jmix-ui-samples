Вы можете использовать любые сторонние аддоны для Vaadin из [Vaadin Directory](https://vaadin.com/directory) или из
других источников.\
В этом примере показана интеграция аддона [Input Mask](https://vaadin.com/directory/component/input-mask-add-on).

Чтобы добавить аддон Vaadin в ваше приложение, добавьте репозиторий Vaadin Add-ons и зависимость на аддон в файле
`build.gradle` проекта:

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

После этого вы сможете использовать компоненты аддона программно, см.
например [InputMaskAddonSimpleSample.java]({currentPath}?tab=InputMaskAddonSimpleSample.java).

Дополнение добавляет компонент `InputMask`, основаный на JavaScript библиотеке [imaskjs](https://imask.js.org/).\
Компонент `InputMask` позволяет добавлять маску ввода к компонентам Vaadin Flow, таким как `TextField` или `DatePicker`.\
Более подробную информации о построении паттерна маски и настройке можно найти на сайте [imaskjs :: guide](https://imask.js.org/guide.html#masked-pattern).
