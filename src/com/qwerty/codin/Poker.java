package com.qwerty.codin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Poker {

	// Straight Flush: Five cards in consecutive order, all of the same suit, such
	// as 5-6-7-8-9 of clubs.
	// Four of a Kind: Four cards of the same rank, such as four 8s.
	// Full House: Three cards of the same rank and two cards of another rank, such
	// as three 9s and two 6s.
	// Flush: Five cards of the same suit, such as five diamonds.
	// Straight: Five cards in consecutive order, such as 8-9-10-Jack-Queen.
	// Three of a Kind: Three cards of the same rank, such as three 6s.
	// Two Pairs: Two cards of the same rank and two cards of another rank, such as
	// two Jacks and two 4s.
	// One Pair: Two cards of the same rank, such as two Jacks.
	// High Card: When all else fails, and you don’t have any of the winning poker
	// hands described above, look at the highest card in your hand.

	public static void main(String[] args) {
		
		String hand1 = "HA,H1,D2,HQ,HK";
		String hand2 = "DK,H7,H1,DT,DA";

		Poker p = new Poker();
		Integer result = p.compare(hand1, hand2);

		if (result > 0) {
			System.out.println("Hand 1 wins");
		} else if (result < 0) {
			System.out.println("Hand 2 wins");
		} else {
			System.out.println("Draw");
		}
	}

	public Integer compare(String hand1, String hand2) {

		Integer rankH1 = identifyHand(hand1);
		Integer rankH2 = identifyHand(hand2);

		if (rankH1 < rankH2) {

			// p1 wins
			return 1;
		} else if (rankH2 < rankH1) {

			// p2 wins
			return -1;
		} else {

			return compareWithinRanks(hand1, hand2, rankH1);
		}
	}

	private Integer identifyHand(String hand) {

		String[] cards = hand.split(",");

		List<Integer> ranks = getRanks(cards);
		List<Character> suits = Stream.of(cards).map(c -> c.charAt(0)).collect(Collectors.toList());

		if (areConsecutive(ranks) && areSameSuit(suits))
			return 1; // straight flush

		else if (hasNOfKind(ranks, 4))
			return 2; // four of a kind

		else if (hasNOfKind(ranks, 3) && numberOfPairs(ranks) > 0)
			return 3; // full house

		else if (areSameSuit(suits))
			return 4; // flush

		else if (areConsecutive(ranks))
			return 5; // straight

		else if (hasNOfKind(ranks, 3))
			return 6; // three of a kind

		else if (numberOfPairs(ranks) == 2)
			return 7; // two pairs

		else if (numberOfPairs(ranks) == 1)
			return 8; // one pair
		else
			return 9; // high card
	}

	private List<Integer> getRanks(String[] cards) {

		return Stream.of(cards).map(c -> c.charAt(1)).map(c -> {
			if (c == 'T')
				return 10;
			else if (c == 'J')
				return 11;
			else if (c == 'Q')
				return 12;
			else if (c == 'K')
				return 13;
			else if (c == 'A')
				return 14;
			else {
				return Integer.parseInt(String.valueOf(c));
			}
		}).sorted().collect(Collectors.toList());
	}

	private Boolean areConsecutive(List<Integer> ranks) {

		for (int i = 0; i < ranks.size() - 1; i++) {

			Integer curr = ranks.get(i);
			Integer next = ranks.get(i + 1);
			Integer diff = next - curr;
			Boolean lastElem = i + 1 == ranks.size() - 1;

			if (diff != 1 && !(lastElem && next == 14 && diff == 9)) {
				return false;
			}
		}
		return true;
	}

	private Boolean areSameSuit(List<Character> suits) {

		return suits.stream().distinct().count() == 1;
	}

	private Boolean hasNOfKind(List<Integer> ranks, long n) {

		return ranks.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting())).entrySet().stream()
				.anyMatch(r -> r.getValue() == n);
	}

	private long numberOfPairs(List<Integer> ranks) {

		return ranks.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting())).entrySet().stream()
				.filter(e -> e.getValue() == 2).count();
	}

	private Integer compareWithinRanks(String hand1, String hand2, Integer rank) {

		List<Integer> r1 = getRanks(hand1.split(","));
		List<Integer> r2 = getRanks(hand2.split(","));
		
		// straight flush, flush, straight, high card
		if (rank == 1 || rank == 4 || rank == 5 || rank == 9) {
			return containsHighestCard(r1, r2);
		}
		
		// four of a kind, three of a kind, one pair
		else if (rank == 2 || rank == 6 || rank == 8) {

			Integer n = rank == 2 ? 4 : rank == 6 ? 3 : 2;
			Integer highN = nthFreqCompare(r1, r2, n);
			
			if(highN==0)
				return containsHighestCard(r1, r2);
			
			return highN;
		}
		
		// full house
		else if (rank == 3) {

			Integer high3 = nthFreqCompare(r1, r2, 3);		
			if(high3==0) {
				
				Integer high2 = nthFreqCompare(r1, r2, 2);
				if(high2==0) 
					return containsHighestCard(r1, r2);
				
				return high2;
			}
			
			return high3;
		}
		
		// two pairs
		else if(rank == 7) {
			
			List<Integer> sortedP1 = getPairs(r1);
			List<Integer> sortedP2 = getPairs(r2);
			
			Integer highPair = containsHighestCard(sortedP1, sortedP2);
			if(highPair == 0)
				return containsHighestCard(r1, r2);
			
			return highPair;
		}

		return 0;
	}

	private List<Integer> getPairs(List<Integer> ranks) {
		
		return ranks.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting())).entrySet().stream().filter(e -> e.getValue()==2).map(e -> e.getKey()).sorted().collect(Collectors.toList());
	}

	private Integer nthFreqCompare(List<Integer> r1, List<Integer> r2, long n) {
		
		Integer h1 = checkNthFreqNo(r1, n);
		Integer h2 = checkNthFreqNo(r2, n);

		if (h1 > h2)
			return 1;
		else if (h2 > h1)
			return -1;
		
		return 0;
	}

	private Integer checkNthFreqNo(List<Integer> ranks, Long n) {

		return ranks.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting())).entrySet().stream().filter(e -> e.getValue()==n).findFirst().get().getKey();
	}

	private Integer containsHighestCard(List<Integer> r1, List<Integer> r2) {

		for (int i = Math.min(r1.size(), r2.size()) - 1; i >= 0; i--) {

			if (r1.get(i) != r2.get(i))
				return r1.get(i) > r2.get(i) ? 1 : -1;
		}

		return 0;
	}

}

