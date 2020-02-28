Hi,

I've followed MVVM architecture. My Activity doesn't know the source of data. I've implemented databinding & used dagger2, retrofit2, RxJava2.
As described in the document, I've used 3 different textviews for showing response of 3 api requests. RxJava's operators such as combineLatest, ZipWith, are used to combine response of separate source.
Wrote an unit test case for testing the app logic.