# Parliament TYpes, Edges

## Vertices

1. Parliaments
2. Electorates
3. Senate Seats (Numbered per state, territory e.g. AU-QLD-1)
4. Parliamentarians (People)
5. Parties

## Edges

1. Electorates to Locations.provinces
2. Countries to Electorates (External territories of Australia)
3. Parliamentarians to Electorates (by Parliament ID)
4. Parliamentarians to Parties (Effective date)
5. Parliamentarians to Senate seats
6. Cabinet to Parliamentarians (by Parliament ID, Date)
7. Parliamentarian to Parliament Role ( by Parliament ID, date, roleName) e.g. Speaker, Prime Minister, Opposition Leader,..
8. Parliamentarian to Party (Date)
