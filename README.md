
# roman-android  
The goal of this app is to help user remember to stay romantic in their relationship as it can be easily forgotten in the midst of the daily mundane routine. At the moment Im using this concept as a playground to familiarise myself with the Compose framework  

## current functionality
- standard app structure with navigation, build using `Compose` framework
- fetch and display restaurant data for fixed location using `KTOR` and `Coroutines`

## build  
to build the app first add new string resource `FOURSQUARE_API_KEY` - it can be obtained from the Foursquare developer [console](https://foursquare.com/developers/)

## todo
- display different type of places and activities using map
- set alerts (notifications, calendar events) that reminds you about romantic gesture  
- more tbd

## known issues
- test do not run from commonTest package
	- https://kotlinlang.org/docs/multiplatform-run-tests.html
	- no mocking native library..
	    - potential solution is to test common code in android test
		    - https://stackoverflow.com/questions/58320492/kotlin-multiplatform-how-to-mock-objects-in-a-unit-test-for-ios
	    - or write own mocks like interface implementation...
- implement multi threading
	- https://kotlinlang.org/docs/multiplatform-mobile-concurrency-and-coroutines.html#multithreaded-coroutines
	- https://medium.com/@carlosdmp/android-ios-kotlin-multiplatform-api-and-threading-17b17e84992f
- use dependency injection in shared module
    - https://johnoreilly.dev/posts/kotlinmultiplatform-koin/
- network response
    - handle java exceptions on android platform
