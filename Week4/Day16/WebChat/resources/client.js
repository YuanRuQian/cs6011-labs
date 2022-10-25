"use strict";

const userNameInput = document.getElementById("user-name")
const roomNameInput = document.getElementById("room-name")
const messageInput = document.getElementById("message-input")
const peopleListContent = document.getElementById("people-list-content")
const peopleCounter = document.getElementById("people-counter")
const messageQueue = document.getElementById("message-queue")

let socket = null
let isLoggedIn = false

const peopleListData = new Set()
const messageListData = []

let isWebSocketOpen = false

const updatePeopleCounter = () => {
    const peopleList = Array.from(peopleListData)
    peopleCounter.textContent = `${peopleList.length} Online User${peopleList.length > 1 ? 's' : ''}`
}

const getNamePrompt = (user) => {
    const isUserThemselves = isCurrentUser(user)
    return user + (isUserThemselves ? "(you)" : "")
}

const addUserNameListItem = (user) => {
    const userNameListItem = document.createElement("li")
    userNameListItem.textContent = getNamePrompt(user)
    if (isCurrentUser(user))
    {
        userNameListItem.style.color = "green"
        userNameListItem.style.fontWeight = "bold"
    }
    peopleListContent.appendChild(userNameListItem)
}

const addNewUser = (user) => {
    peopleListData.add(user)
    peopleListContent.innerHTML = ""
    const peopleList = Array.from(peopleListData)
    peopleList.forEach(addUserNameListItem)
    updatePeopleCounter()
}

const removeUser = (user) => {
    peopleListData.delete(user)
    peopleListContent.innerHTML = ""
    const peopleList = Array.from(peopleListData)
    peopleList.forEach(name => {
        const userNameListItem = document.createElement("li")
        userNameListItem.textContent = name
        peopleListContent.appendChild(userNameListItem)
    })
    peopleCounter.textContent = `${peopleList.length} Online User${peopleList.length > 1 ? 's' : ''}`
}

const getUserNameInputValue = () => userNameInput.value
const getRoomNameInputValue = () => roomNameInput.value
const getMessageInputValue = () => messageInput.value

const isUserNameInputValueValid = () => {
    const userNameInputValue = getUserNameInputValue()
    const isEmptyString = userNameInputValue.length === 0
    const containsSpaces = !!userNameInputValue.match(/\s/)
    return !isEmptyString && !containsSpaces
}
const isRoomNameInputValueValid = () => {
    const roomNameInputValue = getRoomNameInputValue()
    const isEmptyString = roomNameInputValue.length === 0
    const isLowerCased = roomNameInputValue === roomNameInputValue.toLowerCase()
    const containsSpaces = !!roomNameInputValue.match(/\s/)
    return !isEmptyString && isLowerCased && !containsSpaces
}
const isMessageInputValueValid = () => {
    const messageInputValue = getMessageInputValue()
    return messageInputValue.length > 0
}

const checkIfUserCanJoinRoom = () => {
    if (!isUserNameInputValueValid()) {
        alert("Please enter a user name with no spaces!")
        return false
    }
    if (!isRoomNameInputValueValid()) {
        alert("Please enter a lower cased room name with no spaces!")
        return false
    }
    return true
}

const checkIfUserCanSendMessage = () => {
    if (!checkIfUserCanJoinRoom()) {
        return false
    }
    if (!isMessageInputValueValid()) {
        return false
    }
    return true
}

const isCurrentUser = (user) => {
    return user === getUserNameInputValue()
}

const updateDocumentTitle = (user) => {
    if (isCurrentUser(user)) {
        document.title = `${getUserNameInputValue()}'s Chat Room`
    }
}

const addJoinMessage = (user, newMessage) => {
    if (!peopleListData.has(user)) {
        newMessage.textContent = `${user} enters the room`
        newMessage.style.color = "gray"
        newMessage.style.fontWeight = "bold"
        newMessage.style.borderColor = "gray"
        addNewUser(user)
        updateDocumentTitle(user)
    }
}

const addLeaveMessage = (user, newMessage) => {
    if (peopleListData.has(user)) {
        newMessage.textContent = `${user} leaves the room`
        newMessage.style.color = "gray"
        newMessage.style.fontWeight = "bold"
        newMessage.style.borderColor = "gray"
        removeUser(user)
    }
}

const addReceivedMessage = (user, newMessage, message) => {
    const isUserThemselves = isCurrentUser(user)
    if (isUserThemselves) {
        newMessage.style.color = "green"
        newMessage.style.border = "4px green solid"
    }
    newMessage.textContent = `${getNamePrompt(user)}: ${message}`
}

const addNewMessage = (type, room, user, message) => {
    if (room !== getRoomNameInputValue()) {
        return
    }
    if (messageListData.length === 0) {
        messageQueue.innerHTML = ""
    }
    messageListData.push({ type, room, user, message })
    const newMessage = document.createElement('div')
    newMessage.style.border = "2px black solid"
    switch (type) {
        case 'join':
            addJoinMessage(user, newMessage)
            break
        case 'leave':
            addLeaveMessage(user, newMessage)
            break
        case 'message':
            addReceivedMessage(user, newMessage, message)
            break
    }
    messageQueue.appendChild(newMessage)
}

const handleEnterKeyPress = (event) => {
    if (event.key !== 'Enter') {
        return
    }
    if (isWebSocketOpen) {
        if (checkIfUserCanSendMessage()) {
            socket.send(`${getUserNameInputValue()} ${getMessageInputValue()}`)
        }
        else if (checkIfUserCanJoinRoom()) {
            socket.send(`join ${getUserNameInputValue()} ${getRoomNameInputValue()}`)
        }
    }
    else {
        alert("web socket is closed")
    }
}

window.addEventListener("keydown", handleEnterKeyPress)

window.onbeforeunload = function () {
    alert('yes');
};

const handleWindowClose = (event) => {
    if (isWebSocketOpen) {
        socket.send(`leave ${getUserNameInputValue()} ${getRoomNameInputValue()}`)
    }
    else {
        alert("web socket is close")
    }
}

window.onclose = handleWindowClose

const updateUIAfterLoggingIn = () => {
    isLoggedIn = true
    userNameInput.disabled = true
    roomNameInput.disabled = true
    messageInput.disabled = false
}

const clearMessageAndFocus = () => {
    messageInput.value = null
    messageInput.focus()
}





const displayEmptyMessageListTip = () => {
    const emptyMessageListTip = document.createElement('div')
    emptyMessageListTip.textContent = "No message history."
    emptyMessageListTip.style.color = "gray"
    emptyMessageListTip.style.fontWeight = "bold"
    emptyMessageListTip.style.border = "2px gray dashed"
    messageQueue.appendChild(emptyMessageListTip)
}

const handleMessage = (event) => {
    const data = JSON.parse(event.data)
    const { type, room, user, message } = data
    addNewMessage(type, room, user, message)
    switch (type) {
        case 'join':
            updateUIAfterLoggingIn()
            break
        case 'message':
            clearMessageAndFocus()
            break
    }
}

const handleError = (error) => {
    alert("socket error, see console for error message");
    console.error(error)
}

const handleClose = (event) => {
    isWebSocketOpen = false
}

const handleOpen = (event) => {
    isWebSocketOpen = true
}

const setupWebSocket = () => {
    socket = new WebSocket("ws://localhost:8080")
    socket.onopen = handleOpen
    socket.onclose = handleClose
    socket.onerror = handleError
    socket.onmessage = handleMessage
}

const getRandomString = (length) => {
    length = Math.max(1, length)
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

const getRandomUserName = () => {
    const string = getRandomString(Math.floor(Math.random() * 10))
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

const getDefaultRoomName = () => "room42"

window.onload = (event) => {
    setupWebSocket()
    updatePeopleCounter()
    displayEmptyMessageListTip()
    messageInput.disabled = true
    userNameInput.value = getRandomUserName()
    roomNameInput.value = getDefaultRoomName()
}