# SwipeToHideLayout

Simple android lib for hiding out views by draging them out of screen in the set direction.
<br/>Also provides methods for hiding and showing them manually.

<a href="https://imgflip.com/gif/2475bq"><img src="https://i.imgflip.com/2475bq.gif" title="made at imgflip.com"/></a>
<a href="https://imgflip.com/gif/2475hc"><img src="https://i.imgflip.com/2475hc.gif" title="made at imgflip.com"/></a>
<a href="https://imgflip.com/gif/2475j2"><img src="https://i.imgflip.com/2475j2.gif" title="made at imgflip.com"/></a>

### Importing the library
Simply add the following dependency to your build.gradle file:
```
compile 'cz.martinforejt:swipetohidelayout:1.0.2'
```
### Usage
Work with SwipeToHideLayout like with android FrameLayout
```
<cz.martinforejt.swipetohidelayout.SwipeToHideLayout
    android:id="@+id/layout_top"
    android:layout_width="match_parent"
    android:layout_height="160dp"
    android:layout_alignParentTop="true"
    app:direction="top">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="@string/swipe_me_top" />

</cz.martinforejt.swipetohidelayout.SwipeToHideLayout>
```

```
final SwipeToHideLayout top = findViewById(R.id.layout_top);
top.setOnSwipeChangeListener(new OnSwipeChangeListener() {
    @Override
    public void onSwipeChange(boolean visible, SwipeHideable swipeHideable) {
        // catch swipe
    }
});
```
### XML attributes
```
direction - drag direction (left, top, right, bottom)
enabled - is dragging enabled (true, false)
```
### Methods
public methods from 
<a href="https://mfori.github.io/SwipeToHideLayout/cz/martinforejt/swipetohidelayout/SwipeHideable.html">SwipeHideable.java</a>
### Javadoc
<a target="_blank" href="https://mfori.github.io/SwipeToHideLayout/">https://mfori.github.io/SwipeToHideLayout/</a>
