#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h> // For sleep()
#include <curl/curl.h>

// CONFIGURATION
#define API_URL "http://localhost:8080/api/traffic/log"
#define INTERSECTION_COUNT 3

// Data Structure to match your Java DTO
struct TrafficData {
    long roadId;       // Matches "roadId" in your Java DTO
    int vehicleCount;
    double averageSpeed;
};

// Function to send data via HTTP POST
void send_data(struct TrafficData data) {
    CURL *curl;
    CURLcode res;

    // Initialize CURL
    curl = curl_easy_init();
    if(curl) {
        // 1. Set the URL
        curl_easy_setopt(curl, CURLOPT_URL, API_URL);

        // 2. Create the JSON String manually
        char jsonPayload[256];
        sprintf(jsonPayload, "{\"roadId\": %ld, \"vehicleCount\": %d, \"averageSpeed\": %.2f}",
                data.roadId, data.vehicleCount, data.averageSpeed);

        // 3. Set Headers (Content-Type: application/json)
        struct curl_slist *headers = NULL;
        headers = curl_slist_append(headers, "Content-Type: application/json");
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);

        // 4. Set Payload
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, jsonPayload);

        // 5. Perform the Request
        res = curl_easy_perform(curl);

        // 6. Check for Errors
        if(res != CURLE_OK) {
            fprintf(stderr, "❌ SEND FAILED: %s\n", curl_easy_strerror(res));
        } else {
            // Success Message
            printf("🟢 SENT | ID: %ld | Count: %d | Speed: %.2f\n",
                   data.roadId, data.vehicleCount, data.averageSpeed);
        }

        // Cleanup
        curl_slist_free_all(headers);
        curl_easy_cleanup(curl);
    }
}

int main() {
    printf("🚦 C Traffic Sensor Started on Arch Linux...\n");
    printf("📡 Target: %s\n", API_URL);
    printf("--------------------------------------------\n");

    // Random Seed
    srand(time(NULL));

    // Simulation Loop
    while(1) {
        for (long id = 1; id <= INTERSECTION_COUNT; id++) {
            struct TrafficData data;

            // Generate Random Logic
            data.roadId = id;
            data.vehicleCount = (rand() % 100) + 5; // Random count 5-105
            data.averageSpeed = 80.0 - (data.vehicleCount * 0.5); // More cars = slower speed
            if (data.averageSpeed < 5) data.averageSpeed = 5;

            // Send to Backend
            send_data(data);
        }

        // Wait 3 seconds before next batch
        printf("⏳ Waiting...\n");
        sleep(3);
    }

    return 0;
}