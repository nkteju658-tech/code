# Android Kotlin Chat App

A real-time chat application built with Kotlin for Android, powered by Firebase.

## Features

- **User Authentication**: Sign up and login with email/password
- **Real-time Messaging**: Send and receive messages instantly
- **Chat Rooms**: Create and manage multiple chat conversations
- **Firebase Integration**: Built on Firebase Realtime Database and Authentication

## Project Structure

```
app/
├── src/main/kotlin/com/example/chatapp/
│   ├── MainActivity.kt           # Login/Signup screen
│   ├── ChatListActivity.kt       # List of chats
│   ├── ChatActivity.kt           # Individual chat screen
│   ├── NewChatActivity.kt        # Create new chat
│   ├── Models.kt                 # Data models (Chat, Message)
│   └── Adapters.kt              # RecyclerView adapters
├── res/layout/
│   ├── activity_main.xml
│   ├── activity_chat_list.xml
│   ├── activity_chat.xml
│   ├── activity_new_chat.xml
│   ├── item_chat.xml
│   └── item_message.xml
├── res/values/
│   ├── strings.xml
│   └── themes.xml
└── AndroidManifest.xml
```

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android SDK 21+
- Firebase account (free tier available)

### Steps

1. **Clone or download this project**

2. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project
   - Enable Authentication (Email/Password)
   - Enable Realtime Database
   - Add Android app to your Firebase project
   - Download `google-services.json` and place it in `app/` directory

3. **Open in Android Studio**
   - File → Open → Select project directory
   - Wait for Gradle sync

4. **Build and Run**
   - Connect an Android device or start an emulator
   - Click Run → Run 'app'

## Firebase Database Structure

```
chats/
├── {userId}/
│   └── {chatId}:
│       ├── id: string
│       ├── name: string
│       ├── lastMessage: string
│       └── lastUpdated: number

messages/
└── {chatId}/
    └── {messageId}:
        ├── id: string
        ├── senderId: string
        ├── senderName: string
        ├── text: string
        └── timestamp: number
```

## Technologies Used

- **Kotlin**: Modern Android development language
- **Firebase Realtime Database**: Real-time data sync
- **Firebase Authentication**: User authentication
- **AndroidX**: Modern Android libraries
- **Coroutines**: Asynchronous programming
- **RecyclerView**: Efficient list rendering

## Future Enhancements

- Group chats
- User profiles and avatars
- Message search
- Push notifications
- Typing indicators
- Message editing and deletion
- File/image sharing
- Dark mode support

## License

This project is open source and available under the MIT License.
