// display the text on the web page
document.writeln("hello world with document writeln")

// display the text in the console
console.log("hello world with console.log")

// if you want the user to see something then use document.writeln
// if you want to debug then use console.log

// Create an array literal variable (write stuff inside of []) containing a string, a boolean, an int, a floating-point number, and an object.
const arr = ['string', true, 42, 42.42, { 'answer': 42 }]
console.log(arr)

// if you just change arr and don't refresh the page it won't get updated

function addFunction(...args) {
    console.log(`${args.join('+')}: ${args.reduce((prev, curr) => prev + curr, 0)}`)
    console.log("add regular function this:", this)
    console.log("add regular function args:", args)
    console.log("add regular function arguments:", arguments)
}

const obj = {
    addFn: addFunction
}

obj.addFn(1, 2, 3, 4, 5)
// 1+2+3+4+5: 15

const add = (...args) => {
    console.log(`${args.join('+')}: ${args.reduce((prev, curr) => prev + curr, 0)}`)
    console.log("add arrow function this:", this)
    console.log("add arrow function args:", args)
    try { console.log("add arrow function arguments:", arguments) }
    catch (e) {
        console.error(e)
    }
}

obj.addFnConst = add

obj.addFnConst(6, 7, 8, 9, 10)
// 6+7+8+9+10: 40

// regular function VS const arrow function

// regular function gets dynamic this ( depends on the object where it was called )
// arrow function's this always equals to the context where it was defined

// regular function has arguments which contains the list of arguments but not for arrow function


obj.addFn(1, '42', 4.2)
// 1+42+4.2: 1424.2
// the function automatically convert all arguments to strings

obj.addFn(1, true, 4.2)
// 1+true+4.2: 6.2
// the function automatically convert all arguments to numbers

obj.addFn(1, 4.2, 42)
// 1+4.2+42: 47.2
// ints and floats add up cuz they are all numbers