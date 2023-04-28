public record MtgCard(String oracle_id, String name, String scryfall_uri, String mana_cost, String type_line,
                      String oracle_text, MtgCardImages image_uris, MtgCard[] card_faces) {
}
