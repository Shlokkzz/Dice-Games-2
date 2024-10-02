# Dice Games App

## Student Information:
- **Student 1:** Shlok Patel  
  **BITS ID:** 2021A7PS2441G  
- **Student 2:** Suraz Kumar  
  **BITS ID:** 2021B2A71602G  

## Project Overview:
This app is a suite of multiple dice games, allowing players to wager coins and play different variants like "2 alike," "3 alike," and "4 alike" games. The app maintains a wallet balance for the player, which persists across sessions using SharedPreferences. Additionally, the app is designed with accessibility in mind, ensuring smooth navigation with tools like TalkBack.

## Known Bugs:
- No critical bugs identified during testing.
- Minor UI delay when transitioning between fragments under certain conditions.

## Tasks Summary:
1. **Implementing the Wallet ViewModel**: 
   - Developed the ViewModel to pass all the provided test cases.
   - Additional JUnit test cases were created to verify coin balance updates and wager limits.
   - The WalletFragment UI was tied to the ViewModel, ensuring smooth balance updates.

2. **GamesFragment and Game Logic**:
   - Implemented game logic for the "Two or More" game modes (2 alike, 3 alike, 4 alike).
   - Added JUnit test cases to verify the game's betting logic based on wallet balance.
   - The UI for the GamesFragment was designed to follow the specifications and was connected to the ViewModel.

3. **Adding InfoFragment**:
   - Created an InfoFragment and integrated it with the navigation graph.
   - The INFO button loads this fragment, which contains the rules of the game.

4. **Persisting Wallet Balance**:
   - Used SharedPreferences to ensure the wallet balance persists across app kills and device reboots.
   - Implemented state persistence during screen rotations.

5. **Accessibility Enhancements**:
   - Ran the app using TalkBack and found it easy to navigate. Minor color contrast issues were identified and fixed.
   - Integrated Accessibility Scanner and Espresso to verify accessibility standards. Two additional Espresso tests were created to check UI elements' accessibility.

## Testing Strategy:
I followed a test-driven development (TDD) approach, writing test cases before implementing the actual functionality. This helped me avoid issues later in development. Test cases were implemented for both the ViewModel and the UI elements to ensure proper integration. The accessibility of the app was also tested using Espresso and TalkBack.

## Time Spent:
- **Approximate hours taken:** 10 hours

## Difficulty Rating:
- **Difficulty rating (1-10):** 8

## Pair Programming Rating:
- **Rating:** 4 / 5

