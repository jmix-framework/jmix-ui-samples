let map;
let connector;

// Callback for map initialization
initMap = function () {
    map = new google.maps.Map(document.getElementById("google-map"));

    map.addListener("click", (mapsMouseEvent) =>
        // Call a server-side function
        connector.onClick(mapsMouseEvent.latLng.lat(), mapsMouseEvent.latLng.lng()));

    // Set coordinates and zoom
    updateMapOptions();
}

updateMapOptions = function () {
    if (map != null && connector != null) {
        let data = connector.getState().data;
        map.setOptions({
            center: {lat: data.lat, lng: data.lng},
            zoom: data.zoom
        });
    }
}

io_jmix_sampler_screen_ui_components_javascript_component_GoogleMap = function () {
    connector = this;
    let element = connector.getElement();
    element.innerHTML = "<div id=\"google-map\"/>";

    // Handle changes from the server-side
    connector.onStateChange = function () {
        // Set coordinates and zoom
        updateMapOptions();

        // Define a function that can be called from server-side
        connector.addMarker = function (lat, lng, title) {
            new google.maps.Marker({
                position: {lat: lat, lng: lng},
                map,
                title: title
            });
        }
    }

    // Add a script to access the Google Maps API
    const script = document.createElement('script');
    // In the script, you need to pass the Google API key and the callback that will be called to initialize the map
    let data = connector.getState().data;
    script.src = 'https://maps.googleapis.com/maps/api/js?key=' + data.key + '&language=' + data.language + '&callback=initMap';
    script.async = true;
    document.head.appendChild(script);
}
