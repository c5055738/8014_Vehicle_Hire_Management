Project: CSC8014 Vehicle Hire Management System Coursework
Student Name: Wen-Jia Zheng

--------------------------------------------------
1. AI Use Declaration
--------------------------------------------------
According to the course regulations, I would like to clarify how AI was used in this project. AI was mainly used to support discussion and idea checking, rather than to directly generate complete solutions.

Included:
- Architecture discussion: I discussed different class hierarchy designs with AI to better understand their pros and cons. This helped me confirm that my interface-based structure using AbstractVehicle, Car, and Van was reasonable and aligned with the specification.
- Performance and data structures: For example, 3I compared List and HashMap for managing vehicle records. After reviewing the differences, I decided to use HashMap to improve lookup performance.
- Logic: I used AI to confirm whether my implementation logic was correct.
- Debugging: I used AI to help interpret error messages and resolve issues.
- Review: I used AI to review my Javadoc and comments to check if there are errors or spelling mistakes.

--------------------------------------------------
2. Testing
--------------------------------------------------

Included:
- Normal scenarios: Checking that vehicles can be added, hired, and returned correctly, and that mileage is updated properly.
- Boundary conditions:
    * Testing the exact age limits (18 for cars and 23 for vans).
    * Ensuring a customer cannot hire more than three vehicles.
    * Verifying that service resets correctly at 10,000 miles (car) and 5,000 miles (van).
- Exceptional cases: Checking that duplicate customer records are rejected and that underage or unqualified customers cannot hire vehicles.
- Execution and validation: I discussed possible boundary cases with AI to obtain ideas. After that, I wrote, executed, and verified all test cases.

--------------------------------------------------
3. Notes on Implementation
--------------------------------------------------
- Defensive programming: To keep the system stable, I made CustomerRecord, Name, and VehicleID immutable. I also used defensive copying for Date to prevent external modification.
- Collections: I used Map to manage vehicles and customers so that lookups are more efficient. Getter methods return unmodifiable views to protect internal data.
- Implementation process: I discussed design ideas and data structure choices with AI to check my understanding, but the final design decisions and all core business logic were implemented by me.

AI was only used for brainstorming and problem-solving support. All core functionality was written, integrated, and tested independently by me.