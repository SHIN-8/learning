interface Semigroup<A> {
    concat(a1: A, a2: A): A
}

interface Monoid<A> extends Semigroup<A> {
    empty: A,
}

const numberMonoid: Monoid<number> = {
    concat: (a1: number, a2: number) => a1 + a2,
    empty: 0
}

const stringMonoid: Monoid<string> = {
    concat: (a1: string, a2: string) => a1 + a2,
    empty: ""
}

const booleanMonoid: Monoid<boolean> = {
    concat: (a1: boolean, a2: boolean) => a1 && a2,
    empty: true
}

console.assert([1, 2, 3].reduce(numberMonoid.concat, numberMonoid.empty) === 6)
console.assert(["a", "b", "c"].reduce(stringMonoid.concat, stringMonoid.empty) === "abc")
console.assert([true, true, true].reduce(booleanMonoid.concat, booleanMonoid.empty) === true)

