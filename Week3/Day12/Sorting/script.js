
const findMinLocation = (arr, start, compareTo) => {
    let minElementIndex = start
    for (let i = start + 1; i < arr.length; i++) {
        if (compareTo(arr[i], arr[minElementIndex])) {
            minElementIndex = i
        }
    }
    return minElementIndex
}

const selectionSort = (arr, compareTo) => {
    for (let i = 0; i < arr.length; i++) {
        const minElementIndex = findMinLocation(arr, i, compareTo)
        const temp = arr[minElementIndex]
        arr[minElementIndex] = arr[i]
        arr[i] = temp
    }
}

const testSortingInts = () => {
    const arr = Array(10).fill(null).map(_ => Math.floor(Math.random() * 1000))
    document.writeln("before sorting: " + arr + "<br><br>")
    selectionSort(arr, (a, b) => a < b)
    document.writeln("after sorting: " + arr + "<br><br>")
}

testSortingInts()

const testSortingFloats = () => {
    const arr = Array(10).fill(null).map(_ => Math.random() * 1000)
    document.writeln("before sorting: " + arr + "<br><br>")
    selectionSort(arr, (a, b) => a < b)
    document.writeln("after sorting: " + arr + "<br><br>")
}

testSortingFloats()

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

const testSortingStrings = () => {
    const arr = Array(10).fill(null).map(_ => getRandomString(Math.random() * 15))
    document.writeln("before sorting: " + arr + "<br><br>")
    selectionSort(arr, (a, b) => a < b)
    document.writeln("after sorting: " + arr + "<br><br>")
}

testSortingStrings()


const testMixedIntsFloatsAndStrings = () => {
    const ints = Array(5).fill(null).map(_ => Math.floor(Math.random() * 1000))
    const floats = Array(5).fill(null).map(_ => Math.random() * 1000)
    const strings = Array(5).fill(null).map(_ => getRandomString(Math.random() * 15))
    const arr = [...ints, ...floats, ...strings]
    document.writeln("before sorting: " + arr + "<br><br>")
    selectionSort(arr, (a, b) => a < b)
    document.writeln("after sorting: " + arr + "<br><br>")
}

testMixedIntsFloatsAndStrings()

const generateRandomName = () => {
    const string = getRandomString(Math.floor(Math.random() * 10))
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

const generateRandomPerson = () => {
    const firstName = generateRandomName()
    const lastName = generateRandomName()
    return {
        firstName,
        lastName
    }
}

const comparePeople = (a, b) => {
    return a.lastName === b.lastName ? a.firstName < b.firstName : a.lastName < b.lastName
}

const comparePeople2 = (a, b) => {
    return a.firstName === b.firstName ? a.lastName < b.lastName : a.firstName < b.firstName
}

const testSortingPeople = () => {
    const arr = Array(10).fill(null).map(_ => generateRandomPerson())
    document.writeln("before sorting: " + arr.map(JSON.stringify) + "<br><br>")
    selectionSort(arr, comparePeople)
    document.writeln("after sorting: " + arr.map(JSON.stringify) + "<br><br>")
}

testSortingPeople()

const testSortingPeople2 = () => {
    const arr = Array(10).fill(null).map(_ => generateRandomPerson())
    document.writeln("before sorting: " + arr.map(JSON.stringify) + "<br><br>")
    selectionSort(arr, comparePeople2)
    document.writeln("after sorting: " + arr.map(JSON.stringify) + "<br><br>")
}

testSortingPeople2()